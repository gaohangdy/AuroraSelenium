package jp.co.amway.aurora.test.bean;

public class TestActionInfo {

    private String by;
    private String element;
    private String action;
    private String value;
    private boolean screenShot;
    
    public TestActionInfo() {
        by = "";
    }

    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isScreenShot() {
        return screenShot;
    }

    public void setScreenShot(boolean screenShot) {
        this.screenShot = screenShot;
    }
}
