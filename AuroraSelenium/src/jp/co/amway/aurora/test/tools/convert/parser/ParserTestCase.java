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
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

    private List<TestActionInfo> lstTestCaseAction;
    private String filePath;
    private CompilationUnit cu;

    public ParserTestCase(String filePath) {
        this.filePath = filePath;
    }

    public List<TestActionInfo> getLstTestCaseAction() {
        ParserTestActions();
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

    private void ParserTestActions() {
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

//                    ASTHelper.addArgument(call, new StringLiteralExpr("Hello World!"));
                    MethodDeclaration method = (MethodDeclaration) member;
//                    changeMethod(method);
                }
            }
        }
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
        }
    }
}
