
import japa.parser.ASTHelper;
import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jp.co.amway.aurora.test.bean.TestActionInfo;
import jp.co.amway.aurora.test.constant.AuroraSeleniumConst;
import org.openqa.selenium.WebElement;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author epe2
 */
public class MethodChanger {
	private static final List<TestActionInfo> lstTestCaseAction = new ArrayList<TestActionInfo>();
    public static void main(String[] args) throws Exception {
        // creates an input stream for the file to be parsed
        FileInputStream in = new FileInputStream(System.getProperty("user.dir") + "/D01001001.java");

        CompilationUnit cu;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } finally {
            in.close();
        }

        // change the methods names and parameters
        changeMethods(cu);
        refactJavaFile(cu.toString(), System.getProperty("user.dir") + "/D01001001.java");
        formatTestStatement(cu);

        // prints the changed compilation unit
        System.out.println(cu.toString());
    }
    
    private static void formatTestStatement(CompilationUnit cu) {
        List<TypeDeclaration> types = cu.getTypes();

        for (TypeDeclaration type : types) {
            List<BodyDeclaration> members = type.getMembers();
            for (BodyDeclaration member : members) {
                if (member instanceof MethodDeclaration && member.getAnnotations()!= null && "Test".equals(member.getAnnotations().get(0).getName().getName())) {
                	System.out.println("aaa");
                }
            }
        }
    }

    private static void changeMethods(CompilationUnit cu) {
        
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

//                    ASTHelper.addArgument(call, new StringLiteralExpr("Hello World!"));
                    MethodDeclaration method = (MethodDeclaration) member;
//                    changeMethod(method);
                }
            }
        }
    }

    private static void checkAction(TestActionInfo testActionInfo, MethodCallExpr methodCallExpr) {
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

    private static void setActionValue(TestActionInfo testActionInfo, MethodCallExpr methodCallExpr) {
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

    private static void fetchByStatement(MethodCallExpr methodCallExpr, TestActionInfo testActionInfo) {

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
            
            
            checkAction(testActionInfo, methodCallExpr);
            fetchByStatement((MethodCallExpr) methodCallExpr.getScope(), testActionInfo);
        }
    }

    private TestActionInfo formatTestCaseAction(String statment) {
        TestActionInfo action = new TestActionInfo();


        return action;
    }

    private static void changeMethod(MethodDeclaration n) {
        // change the name of the method to upper case
        n.setName(n.getName().toUpperCase());

        // create the new parameter
        Parameter newArg = ASTHelper.createParameter(ASTHelper.INT_TYPE, "value");

        // add the parameter to the method
        ASTHelper.addParameter(n, newArg);
    }
    
	
	private static void refactJavaFile(String source, String path) throws IOException {
		File fConvert = new File(path);
		FileWriter fstream = new FileWriter(fConvert);
		BufferedWriter outobj = new BufferedWriter(fstream);
		outobj.write(source);
		outobj.close();
	}
}
