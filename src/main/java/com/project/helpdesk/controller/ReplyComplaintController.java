package com.project.helpdesk.controller;

import com.project.helpdesk.constant.ApiUrl;
import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.NewComplaintReplyDtoRequest;
import com.project.helpdesk.dto.request.UpdateComplaintReplyDtoRequest;
import com.project.helpdesk.dto.response.CommonResponse;
import com.project.helpdesk.dto.response.ComplaintReplyDtoResponse;
import com.project.helpdesk.service.ComplaintReplyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.COMPLAINT_URL + "/reply")
public class ReplyComplaintController {
    private final ComplaintReplyService complaintReplyService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TECHNICIAN')")
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

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TECHNICIAN')")
    @PutMapping
    public ResponseEntity<CommonResponse<ComplaintReplyDtoResponse>> updateComplaintReply(@RequestBody UpdateComplaintReplyDtoRequest request) {
        ComplaintReplyDtoResponse updatedComplaintReply = complaintReplyService.updateComplaintReply(request);

        CommonResponse<ComplaintReplyDtoResponse> response = CommonResponse.<ComplaintReplyDtoResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(updatedComplaintReply)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
