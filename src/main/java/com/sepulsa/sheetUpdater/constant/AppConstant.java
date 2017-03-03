package com.sepulsa.sheetUpdater.constant;

public interface AppConstant {

    public static final String SHEET_MAPPING_FILE = "sheetMapping.json";
    public static final String ACTIVITY_CREATE = "story_create_activity";
    public static final String ACTIVITY_MOVE = "story_move_activity";
    public static final String ACTIVITY_UPDATE = "story_update_activity";
    public static final Integer DEFAULT_PORT = 8181;
    public static final String KIND_STORY = "story";
    public static final Integer FLAG_SUCCESS = 1;
    public static final Integer FLAG_FAILED  = 0;
    
    public static final String[] PIVOTAL_FIELD_MAIN = new String[] {"kind","guid","project_version","message","highlight"};
    public static final String[] PIVOTAL_FIELD_PRIMARY_RES = new String[] {"id","name","url"};
    public static final String[] PIVOTAL_FIELD_PROJECT = new String[] {"project_kind","project_name","project_id"};
    public static final String[] PIVOTAL_FIELD_DATETIME = new String[] {"created_at","updated_at"};
    
}
