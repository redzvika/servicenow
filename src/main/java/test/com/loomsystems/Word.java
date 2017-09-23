package test.com.loomsystems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Word {

    @Getter private String value;
    @Getter private int positionInSentence;

    @Override
    public String toString(){
        return "word="+value+" ,position="+positionInSentence;
    }

}
