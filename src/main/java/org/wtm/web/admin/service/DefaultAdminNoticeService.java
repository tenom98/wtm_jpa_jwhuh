package org.wtm.web.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wtm.web.admin.dto.notice.NoticeCreateDto;
import org.wtm.web.admin.dto.notice.NoticeDto;
import org.wtm.web.admin.dto.notice.NoticeListDto;
import org.wtm.web.admin.dto.notice.NoticeUpdateDto;
import org.wtm.web.admin.mapper.AdminNoticeMapper;
import org.wtm.web.admin.repository.AdminNoticeRepository;
import org.wtm.web.admin.repository.AdminReviewRepository;
import org.wtm.web.admin.repository.AdminStoreRepository;
import org.wtm.web.admin.repository.AdminUserRepository;
import org.wtm.web.store.model.Notice;
import org.wtm.web.store.model.Store;
import org.wtm.web.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultAdminNoticeService implements AdminNoticeService {

    private final AdminNoticeRepository noticeRepository;
    private final AdminReviewRepository reviewRepository;
    private final AdminStoreRepository storeRepository;
    private final AdminUserRepository userRepository;

    private final AdminNoticeMapper noticeMapper;

    /**
     * 공지사항 조회
     */
    public List<NoticeListDto> getNoticesByStoreId(Long storeId) {
        List<Notice> notices = noticeRepository.findNoticesWithUserByStoreId(storeId);
        return notices.stream().map(noticeMapper::toNoticeListDto).collect(Collectors.toList());
    }

    /**
     * 공지사항 등록
     */
    public Notice createNotice(Long storeId, NoticeCreateDto noticeCreateDto) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("스토어를 찾을 수 없습니다."));
        User user = userRepository.findById(noticeCreateDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Notice notice = noticeMapper.toNoticeEntity(noticeCreateDto, store, user);

        return noticeRepository.save(notice);
    }

    /**
     * 공지사항 수정
     */
    @Transactional
    public NoticeDto updateNotice(Long storeId, Long noticeId, NoticeUpdateDto noticeUpdateDto) {
        Notice notice = noticeRepository.findByStoreIdAndId(storeId, noticeId)
                .orElseThrow(()-> new IllegalArgumentException("공지사항을 찾을 수 없습니다"));

        noticeMapper.updateNoticeFromDto(noticeUpdateDto, notice);

        Notice updatedNotice = noticeRepository.save(notice);

        return noticeMapper.toNoticeDto(updatedNotice);
    }

    /**
     * 공지사항 삭제
     */
    @Transactional
    public void deleteNotice(Long storeId, Long noticeId) {
        Notice notice = noticeRepository.findByStoreIdAndId(storeId, noticeId)
                .orElseThrow(()-> new IllegalArgumentException("공지사항을 찾을 수 없습니다"));
        noticeRepository.delete(notice);
    }


}
