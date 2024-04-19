package com.example.chatting.Entity;
public class ChatMessage {
    private String name;
    private String content;

    public ChatMessage(String name, String content) {
        this.name = name;
        this.content = content;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}