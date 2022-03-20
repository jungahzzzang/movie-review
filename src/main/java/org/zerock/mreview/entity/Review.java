package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = {"movie", "member"})
public class Review extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewnum;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private int grade;
    private String text;

    //리뷰 평점과 리뷰 내용을 수정할 수 있는 기능
    public void changeGrade(int grade){
        this.grade=grade;
    }

    public void changeText(String text){
        this.text = text;
    }
}
