package com.app.pupacic.project1;


public class Note {
    private	int	id;
    private	String	title;
    private	String	content;
    private String link;
    private byte[] image;

    public int getId() {
        return id;
    }

    public void	setId(int id) {
        this.id	= id;
    }

    public String getTitle() {
        return title;
    }

    public void	setTitle(String	title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void	setContent(String content) {
        this.content = content;
    }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public byte[] getImage() { return image; }

    public void setImage(byte[] image) { this.image = image; }


    @Override
    public String toString() {
        return this.title;
    }

}
