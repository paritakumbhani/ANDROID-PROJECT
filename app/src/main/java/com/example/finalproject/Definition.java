package com.example.finalproject;

public class Definition {

    long id;
    String title;
    String definition;

    public Definition(Long id, String title, String definition) {

        this.id = id;//id
        this.title = title;//title
        this.definition = definition;//definition

    }

    public Definition() {

        title = null;
        definition = null;

    }

    /**
     * getid
     *
     * @return
     */
    public Long getid() {

        return id;
    }

    /**
     * get title
     * @return
     */
    public String getTitle() {

        return title;
    }

    /**
     * set title
     * @param title
     */
    public void setTitle(String title) {

        this.title = title;
    }

    /**
     * get definition
     * @return
     */
    public String getDefinition() {

        return definition;
    }

    /**
     * set defination
     * @param definition
     */
    public void setDefinition(String definition) {

        this.definition = definition;
    }

}