package com.easyone.travelance.domain.travel.dto;

import com.easyone.travelance.domain.travel.entity.Notice;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeAllResponseDto {
    private Long noticeId;
    private String title;
    private String content;
    private String link;

    public NoticeAllResponseDto(Notice notice) {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.link = notice.getLink();
    }
}
