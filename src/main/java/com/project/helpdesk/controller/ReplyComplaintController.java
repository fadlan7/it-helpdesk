package com.project.helpdesk.controller;

import com.project.helpdesk.constant.ApiUrl;
import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.NewComplaintReplyDtoRequest;
import com.project.helpdesk.dto.response.CommonResponse;
import com.project.helpdesk.dto.response.ComplaintReplyDtoResponse;
import com.project.helpdesk.service.ComplaintReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.COMPLAINT_URL + "/reply")
public class ReplyComplaintController {
    private final ComplaintReplyService complaintReplyService;

    @PostMapping
    public ResponseEntity<CommonResponse<ComplaintReplyDtoResponse>> createNewEmployee(@RequestBody NewComplaintReplyDtoRequest request) {

        ComplaintReplyDtoResponse newReply = complaintReplyService.createComplaintReply(request);
        CommonResponse<ComplaintReplyDtoResponse> response = CommonResponse.<ComplaintReplyDtoResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(newReply)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
