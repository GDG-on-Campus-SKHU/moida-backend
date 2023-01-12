package gdsc.skhu.moida.domain;

public enum PostType {
    PROJECT, STUDY, MEAL;

    public static PostType getType(String value) {
        if(value.equals("PROJECT")) {
            return PostType.PROJECT;
        } else if(value.equals("STUDY")) {
            return PostType.STUDY;
        } else if(value.equals("MEAL")) {
            return PostType.MEAL;
        }
        return null;
    }
}
