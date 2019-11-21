package com.wecode.entity.util;

public class UserProjectVM {
    private Long idUser;
    private Long idProject;
    private boolean manager;
    public UserProjectVM() {
    }

    public UserProjectVM(Long idUser, Long idProject) {
        this.idUser = idUser;
        this.idProject = idProject;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdProject() {
        return idProject;
    }

    public void setIdProject(Long idProject) {
        this.idProject = idProject;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }
}
