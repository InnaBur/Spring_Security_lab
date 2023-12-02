package com.todo.enums;

import java.util.List;

public enum TaskStatus {

    PLANNED {
        @Override
        public List<TaskStatus> nextStatus() {
            return List.of(IN_PROGRESS, NOTIFIED, DONE, CANCELED);
        }

        @Override
        public boolean immutableStatus() {
            return false;
        }
    },
    IN_PROGRESS {
        @Override
        public List<TaskStatus> nextStatus() {
            return List.of(NOTIFIED, DONE, CANCELED);
        }

        @Override
        public boolean immutableStatus() {
            return false;
        }
    },
    CANCELED {
        @Override
        public List<TaskStatus> nextStatus() {
            return List.of(this);
        }

        public boolean immutableStatus() {
            return true;
        }
    },
    NOTIFIED {
        @Override
        public boolean immutableStatus() {
            return false;
        }

        @Override
        public List<TaskStatus> nextStatus() {
            return List.of(DONE, CANCELED);
        }
    },
    DONE {
        @Override
        public List<TaskStatus> nextStatus() {
            return List.of(this);
        }

        public boolean immutableStatus() {
            return true;
        }
    };

    public abstract boolean immutableStatus();

    public abstract List<TaskStatus> nextStatus();
}
