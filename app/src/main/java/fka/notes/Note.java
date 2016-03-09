package fka.notes;


/**
 * Created by FKA on 06.03.2016.
 */
public class Note {

    private int integer_NoteId;
    private String string_NoteText;

    // Constructors
    public Note(){}

    public Note(int integer_NoteId, String string_NoteText){
        this.integer_NoteId = integer_NoteId;
        this.string_NoteText = string_NoteText;
    }

    public Note(String string_NoteText){
        this.string_NoteText = string_NoteText;
    }

    // Getters and Setters
    public int getInteger_NoteId() {
        return integer_NoteId;
    }

    public void setInteger_NoteId(int integer_NoteId) {
        this.integer_NoteId = integer_NoteId;
    }

    public String getString_NoteText() {
        return string_NoteText;
    }

    public void setString_NoteText(String string_NoteText) {
        this.string_NoteText = string_NoteText;
    }

}
