package com.ilman.music.dto;

public class AkunAdminDto {
    
    public static class New {
        private String id;
        private String username;
        private String keyword;

        public String getId() {
            return this.id;
        }

        public void setId(String Id) {
            this.id = Id;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getKeyword() {
            return this.keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

    }
    
}
