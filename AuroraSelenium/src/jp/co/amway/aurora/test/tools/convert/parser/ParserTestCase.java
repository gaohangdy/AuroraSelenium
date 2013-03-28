/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.amway.aurora.test.tools.convert.parser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jp.co.amway.aurora.test.bean.TestActionInfo;
import jp.co.amway.aurora.test.constant.AuroraSeleniumConst;

/**
 *
 * @author epe2
 */
public class ParserTestCase {

    private List<TestActionInfo> lstTestCaseAction = new ArrayList<TestActionInfo>();
    private String filePath;
    private CompilationUnit cu;
    private List<TestActionInfo> lstFromXls = new ArrayList<TestActionInfo>();

    public ParserTestCase(String filePath) {
        this.filePath = filePath;
    }
    
    public ParserTestCase(String filePath, List<TestActionInfo> lstFromXls) {
    	this.filePath = filePath;
    	this.lstFromXls = lstFromXls;
    }

    public List<TestActionInfo> getLstTestCaseAction() {
        try {
			ParserTestActions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return lstTestCaseAction;
    }

    public void loadJavaFile() throws FileNotFoundException, ParseException, IOException {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream(filePath);

        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }
    }

    private void ParserTestActions() throws IOException {
        try {
            loadJavaFile();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ParserTestCase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ParserTestCase.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ParserTestCase.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<TypeDeclaration> types = cu.getTypes();

        for (TypeDeclaration type : types) {
            List<BodyDeclaration> members = type.getMembers();
            for (BodyDeclaration member : members) {
                if (member instanceof MethodDeclaration) {
                    if (member.getAnnotations() != null) {
                        System.out.println(member.getAnnotations().get(0).getName());
                        if (new NameExpr("Test").equals(member.getAnnotations().get(0).getName())) {
                            System.out.println("Find TestCase Method!");
                            MethodDeclaration method = (MethodDeclaration) member;
                            List<Statement> lstStatement = method.getBody().getStmts();
                            
                            for (Statement stat : lstStatement) {
                                ExpressionStmt eStat = (ExpressionStmt) stat;
                                MethodCallExpr expr = (MethodCallExpr) eStat.getExpression();
//                                System.out.println(((NameExpr) expr.getScope()).getName());
//                                System.out.println(expr.getName());
                                TestActionInfo testActionInfo = new TestActionInfo();
                                fetchByStatement(expr, testActionInfo);
                                if (!"".equals(testActionInfo.getBy())) {
                                    lstTestCaseAction.add(testActionInfo);
                                }
                                System.out.println(stat.toString());
                            }
                        }
                    }
                    
                    MethodDeclaration method = (MethodDeclaration) member;
                }
            }
        }
        
        refactJavaFile(cu.toString(), filePath);
    }

    private void checkAction(TestActionInfo testActionInfo, MethodCallExpr methodCallExpr) {
        for (String item : AuroraSeleniumConst.WEBELEMENT_ACTION) {
            if (methodCallExpr.getName().equals(item)) {
                testActionInfo.setAction(item);
                setActionValue(testActionInfo, methodCallExpr);
                return;
            }
        }
        for (String item : AuroraSeleniumConst.SELECT_ACTION) {
            if (methodCallExpr.getName().equals(item)) {
                testActionInfo.setAction(item);
                setActionValue(testActionInfo, methodCallExpr);
                return;
            }
        }
    }

    private void setActionValue(TestActionInfo testActionInfo, MethodCallExpr methodCallExpr) {
        StringBuilder sbParameters = new StringBuilder();
        if (methodCallExpr.getArgs() == null) {
            return;
        }
        for (Expression itemCallExpr : methodCallExpr.getArgs()) {
            if (sbParameters.length() == 0) {
                sbParameters.append(itemCallExpr.toString());
            } else {
                sbParameters.append(", ").append(itemCallExpr.toString());
            }
        }
        if (sbParameters.length() != 0) {
            testActionInfo.setValue(sbParameters.toString());
        }
    }

    private void fetchByStatement(MethodCallExpr methodCallExpr, TestActionInfo testActionInfo) {

        if (methodCallExpr.getScope() instanceof NameExpr) {
            System.out.println(((NameExpr) methodCallExpr.getScope()).getName() + "." + methodCallExpr.getName());
            if ("By".equals(((NameExpr) methodCallExpr.getScope()).getName())) {
                testActionInfo.setBy(methodCallExpr.getName());
            } else {
                checkAction(testActionInfo, methodCallExpr);
            }
//            System.out.println(methodCallExpr.getName());

            if (!methodCallExpr.getArgs().isEmpty()) {
                for (Expression itemCallExpr : methodCallExpr.getArgs()) {
                    if (itemCallExpr instanceof MethodCallExpr) {
                        fetchByStatement((MethodCallExpr) itemCallExpr, testActionInfo);
                    } else {
                        if ("By".equals(((NameExpr) methodCallExpr.getScope()).getName())) {
                            testActionInfo.setElement(itemCallExpr.toString());
                        }
                        System.out.println(itemCallExpr.toString());
                    }
                }
            }
        } else if (methodCallExpr.getScope() instanceof MethodCallExpr) {
            System.out.println(methodCallExpr.getName());
            checkAction(testActionInfo, methodCallExpr);
            fetchByStatement((MethodCallExpr) methodCallExpr.getScope(), testActionInfo);
            //Replace driver.findElement() to AuroraTestCase.findElement()
            ((MethodCallExpr)methodCallExpr.getScope()).setScope(null);
            if (methodCallExpr.getArgs() != null && !methodCallExpr.getArgs().isEmpty()) {
            	MethodCallExpr tmpMdExpr = new MethodCallExpr();
            	StringLiteralExpr tmpSExpr = new StringLiteralExpr();
            	tmpMdExpr.setName("getTestValue");
            	tmpSExpr.setValue(((MethodCallExpr)methodCallExpr.getScope()).getArgs().get(0).toString().replace("\"", "\\\""));
            	List<Expression> lstArgs = new ArrayList<Expression>();
            	lstArgs.add(tmpSExpr);
            	StringLiteralExpr tmpActionSExpr = new StringLiteralExpr();
            	tmpActionSExpr.setValue(methodCallExpr.getName());
            	lstArgs.add(tmpActionSExpr);
            	tmpMdExpr.setArgs(lstArgs);
            	
            	methodCallExpr.getArgs().set(0, tmpMdExpr);
            }            
        } else if (methodCallExpr.getScope() instanceof ObjectCreationExpr) {
        	System.out.println(methodCallExpr.getName());
            if (methodCallExpr.getArgs() != null && !methodCallExpr.getArgs().isEmpty()) {
            	MethodCallExpr tmpMdExpr = new MethodCallExpr();
            	StringLiteralExpr tmpSExpr = new StringLiteralExpr();
            	tmpMdExpr.setName("getTestValue");
            	
            	MethodCallExpr elementMdExpr = (MethodCallExpr)((ObjectCreationExpr)methodCallExpr.getScope()).getArgs().get(0);
            	//Replace driver.findElement() to AuroraTestCase.findElement()
            	elementMdExpr.setScope(null);
            	tmpSExpr.setValue(elementMdExpr.getArgs().get(0).toString().replace("\"", "\\\""));
            	List<Expression> lstArgs = new ArrayList<Expression>();
            	lstArgs.add(tmpSExpr);
            	StringLiteralExpr tmpActionSExpr = new StringLiteralExpr();
            	tmpActionSExpr.setValue(methodCallExpr.getName());
            	lstArgs.add(tmpActionSExpr);
            	tmpMdExpr.setArgs(lstArgs);
            	
            	methodCallExpr.getArgs().set(0, tmpMdExpr);
            }
            
            
            checkAction(testActionInfo, methodCallExpr);
            fetchByStatement((MethodCallExpr)((ObjectCreationExpr) methodCallExpr.getScope()).getArgs().get(0), testActionInfo);
        }
    }
	
	private void refactJavaFile(String source, String path) throws IOException {
		File fConvert = new File(path);
		FileWriter fstream = new FileWriter(fConvert);
		BufferedWriter outobj = new BufferedWriter(fstream);
		outobj.write(source);
		outobj.close();
	}
}
