package mdhtr.webapplication.persistence;

import lombok.Data;

import java.io.Serializable;

@Data
public class Message implements Serializable{
    private final int id;
    private final String message;
}
