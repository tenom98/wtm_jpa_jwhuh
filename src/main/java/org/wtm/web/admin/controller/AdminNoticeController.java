package org.wtm.web.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wtm.web.admin.dto.notice.NoticeCreateDto;
import org.wtm.web.admin.dto.notice.NoticeDto;
import org.wtm.web.admin.dto.notice.NoticeListDto;
import org.wtm.web.admin.dto.notice.NoticeUpdateDto;
import org.wtm.web.admin.service.AdminNoticeService;
import org.wtm.web.store.model.Notice;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;

    @GetMapping("/stores/{storeId}/notices")
    public List<NoticeListDto> getNotices(@PathVariable Long storeId){
        return adminNoticeService.getNoticesByStoreId(storeId);
    }

    @PostMapping("/stores/{storeId}/notices")
    public ResponseEntity<?> createNotice(@PathVariable Long storeId, @RequestBody NoticeCreateDto noticeCreateDto){
        try {
            Notice notice = adminNoticeService.createNotice(storeId, noticeCreateDto);
            return new ResponseEntity<>(notice, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/stores/{storeId}/notices/{noticeId}")
    public ResponseEntity<?> updateNotice(
            @PathVariable Long storeId,
            @PathVariable Long noticeId,
            @RequestBody NoticeUpdateDto noticeUpdateDto) {
        try {
            NoticeDto updatedNotice = adminNoticeService.updateNotice(storeId, noticeId, noticeUpdateDto);
            return new ResponseEntity<>(updatedNotice, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/stores/{storeId}/notices/{noticeId}")
    public ResponseEntity<?> deleteNotice(
            @PathVariable Long storeId,
            @PathVariable Long noticeId) {
        try {
            adminNoticeService.deleteNotice(storeId, noticeId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
