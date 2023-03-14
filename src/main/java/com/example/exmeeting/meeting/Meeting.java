package com.example.exmeeting.meeting;

import com.example.exmeeting.account.Account;
import com.example.exmeeting.meeting.dto.MeetingCreateDto;
import com.example.exmeeting.meeting.dto.MeetingOnlyTitleDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String subtitle;

    @Column(nullable = false)
    @Lob
    private String body;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public void editMeeting(MeetingCreateDto meetingEditDto) {
        this.title = meetingEditDto.getTitle();
        this.subtitle = meetingEditDto.getSubtitle();;
        this.body = meetingEditDto.getBody();
        this.location = meetingEditDto.getLocation();
    }
}
