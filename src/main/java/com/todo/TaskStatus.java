package com.todo;

public enum TaskStatus {

    PLANNED {
        @Override
        public TaskStatus nextStatus() {
            return IN_PROGRESS;
        }
        @Override
        public boolean immutableStatus() {
            return false;
        }
    },
    IN_PROGRESS {
        @Override
        public TaskStatus nextStatus() {
            return NOTIFIED;
        }
        @Override
        public boolean immutableStatus() {
            return false;
        }
    },
    CANCELED {
        @Override
        public TaskStatus nextStatus() {
            return this;
        }
        public boolean immutableStatus() {
            return  true;
        }
    },
    NOTIFIED {
        @Override
        public boolean immutableStatus() {
            return false;
        }

        @Override
        public TaskStatus nextStatus() {
            return DONE;
        }
    },
    DONE {
        @Override
        public TaskStatus nextStatus() {
            return this;
        }

        public boolean immutableStatus() {
            return  true;
        }
    }
    ;

public abstract  boolean immutableStatus();
    public abstract TaskStatus nextStatus();
}
