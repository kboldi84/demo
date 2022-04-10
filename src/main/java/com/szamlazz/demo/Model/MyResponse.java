package com.szamlazz.demo.Model;
    public class MyResponse<T> {
        public MyResponse(String status, T object) {
            
            this.status = status;
            this.object = object;
        }
        public String getStatus() {
            return status;
        }
        public void setStatus(String status) {
            this.status = status;
        }
        public T getObject() {
            return object;
        }
        public void setObject(T object) {
            this.object = object;
        }
        private String status;
        private T object;
    }

