package com.tidal.wave.command;


public class Commands {

    public enum ClickCommands {

        CHECK("checkAction"),
        CLEAR("clearAction"),
        CLEAR_AND_TYPE("clearAndTypeAction"),
        CLICK("clickAction"),
        CLICK_AND_HOLD("clickAndHoldAction"),
        CLICK_BY_ACTION("clickAction"),
        CLICK_BY_JS("clickByJSAction"),
        DOUBLE_CLICK("doubleClickAction"),
        FORCE_CLICK("forceClickAction"),
        RIGHT_CLICK("rightClickAction"),
        UNCHECK("unCheckAction");

        private final String command;

        ClickCommands(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    public enum GetCommands {
        FIND_TEXT_DATA("findTextDataAction"),
        FIND_ALL_TEXT_DATA("findAllTextDataAction"),
        GET_ALL_ATTRIBUTES("getAllAttributesAction"),
        GET_ALL_CSS_ATTRIBUTES("getAllCssAttributesAction"),
        GET_ATTRIBUTE("getAttributeAction"),
        GET_CSS_ATTRIBUTE("getCssAttributeAction"),
        GET_DIMENSION("getDimensionAction"),
        GET_LOCATION("getLocationAction"),
        GET_RECT("getRectAction"),
        GET_SIZE("getSizeAction"),
        GET_TAG_NAME("getTagNameAction");

        private final String command;

        GetCommands(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    public enum InputCommands {
        SEND_KEYS("sendKeysAction"),
        INPUT_KEYS("keyInputsAction"),
        SET_INNER_HTML("setInnerHtmlAction"),
        SET_TEXT("setTextAction"),
        SET_VALUE("setValueAction"),
        UPLOAD_FILE("fileUploadAction");

        private final String command;

        InputCommands(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    public enum StateCheckCommands {
        IS_ENABLED("isEnabledAction"),
        IS_PRESENT("isPresentAction"),
        IS_PRESENT_AND_DISPLAYED("isPresentAndVisibleAction"),
        IS_SELECTED("isSelectedAction"),
        IS_VISIBLE("isVisibleAction");

        private final String command;

        StateCheckCommands(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    public enum SelectCommands {
        SELECT_BY_TEXT("selectByTextAction"),
        SELECT_BY_VALUE("selectByValueAction"),
        SELECT_BY_INDEX("selectByIndexAction");

        private final String command;

        SelectCommands(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    public enum MoveCommands {
        SCROLL_TO_VIEW("scrollToViewAction"),
        SCROLL_PAGE("scrollPageAction"),
        MOVE_TO_ELEMENT("moveToElementAction"),
        MOVE_BY_OFFSET("moveByOffSetAction"),
        HOVER("hoverAction"),
        DRAG_AND_DROP("dragAndDropAction"),
        DRAG_AND_DROP_BY_OFFSET("dragAndDropByOffsetAction");

        private final String command;

        MoveCommands(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    public enum ZoomCommands {
        ZOOM("zoomAction");

        private final String command;

        ZoomCommands(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    public enum KeyCommands {
        PRESS_ENTER_ACTION("pressEnterAction"),
        PRESS_TAB_ACTION("pressTabAction");

        private final String command;

        KeyCommands(String command) {
            this.command = command;
        }

        @Override
        public String toString() {
            return command;
        }
    }

    public enum PageCommands{
        REFRESH_PAGE_ACTION("pageRefreshAction");

        private final String command;

        PageCommands(String command){
            this.command = command;
        }

        @Override
        public String toString(){
            return command;
        }
    }

}



