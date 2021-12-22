package ru.itis;

public class Session {

    private String p1Name;
    private String p2Name;
    private boolean isP1Win;
    private boolean isP2Winn;

    public Session(String p1Name, String p2Name, boolean isP1Win, boolean isP2Winn) {
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.isP1Win = isP1Win;
        this.isP2Winn = isP2Winn;
    }

    public String getP1Name() {
        return p1Name;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    public boolean isP1Win() {
        return isP1Win;
    }

    public void setP1Win(boolean p1Win) {
        isP1Win = p1Win;
    }

    public boolean isP2Winn() {
        return isP2Winn;
    }

    public void setP2Winn(boolean p2Winn) {
        isP2Winn = p2Winn;
    }
}
