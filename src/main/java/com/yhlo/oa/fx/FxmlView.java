package com.yhlo.oa.fx;

import java.util.ResourceBundle;

public enum FxmlView {
    MAIN {
        @Override
		public String title() {
            return getStringFromResourceBundle("app.title");
        }

        @Override
		public String fxml() {
            return "/views/Main.fxml";
        }

    }, 
    VIEW_FRAMEWORK {
        @Override
		public String title() {
            return getStringFromResourceBundle("module.frameWork.title");
        }

        @Override
        public String fxml() {
            return "/views/FrameWork.fxml";
        }

    },
    VIEW_LOGIN {
        @Override
        public String title() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String fxml() {
            return "/views/Login.fxml";
        }

    },
    VIEW_ROLES {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.roles.title");
        }

        @Override
        public String fxml() {
            return "/views/Roles.fxml";
        }

    },
    VIEW_NORMAL {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.normal.title");
        }

        @Override
        public String fxml() {
            return "/views/Normal.fxml";
        }

    },
    VIEW_CONFIG {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.VIEW_CONFIG_SYN.title");
        }

        @Override
        public String fxml() {
            return "/views/SapConfigTable.fxml";
        }

    },
    VIEW_DATASYN {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.DataSyn.title");
        }

        @Override
        public String fxml() {
            return "/views/DataSyn.fxml";
        }

    },VIEW_WEBVIEW {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.Webview.title");
        }

        @Override
        public String fxml() {
            return "/views/Webview.fxml";
        }

    },VIEW_MATERIAMASTER {
        @Override
        public String title() {
            return getStringFromResourceBundle("module.materiaMaster.title");
        }

        @Override
        public String fxml() {
            return "/views/MateriaMasterData.fxml";
        }

    };


	
    
    public abstract String title();
    public abstract String fxml();
    
    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
