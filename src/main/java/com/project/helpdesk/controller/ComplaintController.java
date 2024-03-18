package com.project.helpdesk.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.helpdesk.constant.ApiUrl;
import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.dto.request.NewComplaintRequest;
import com.project.helpdesk.dto.request.SearchComplaintRequest;
import com.project.helpdesk.dto.request.UpdateComplaintRequest;
import com.project.helpdesk.dto.response.CommonResponse;
import com.project.helpdesk.dto.response.ComplaintDtoResponse;
import com.project.helpdesk.dto.response.GetComplaintDtoResponse;
import com.project.helpdesk.dto.response.PagingResponse;
import com.project.helpdesk.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiUrl.COMPLAINT_URL)
public class ComplaintController {
    private final ComplaintService complaintService;
    private final ObjectMapper objectMapper;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    public ResponseEntity<CommonResponse<ComplaintDtoResponse>> createNewComplaint(
            @RequestPart(name = "complaint") String jsonComplaint,
            @RequestPart(name = "image", required = false) MultipartFile image
    ) {
        CommonResponse.CommonResponseBuilder<ComplaintDtoResponse> responseBuilder = CommonResponse.builder();

        try {
            NewComplaintRequest request = objectMapper.readValue(jsonComplaint, new TypeReference<>() {
            });
            request.setComplaintImage(image);

            ComplaintDtoResponse complainDtoResponse = complaintService.createComplaint(request);

            responseBuilder.statusCode(HttpStatus.CREATED.value());
            responseBuilder.message(ResponseMessage.SUCCESS_SAVE_DATA);
            responseBuilder.data(complainDtoResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBuilder.build());
        } catch (Exception e) {
            responseBuilder.message(ResponseMessage.ERROR_INTERNAL_SERVER);
            responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }

    @PutMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE

    )
    public ResponseEntity<CommonResponse<?>> updateComplaint(
            @RequestPart(name = "complaint") String jsonComplaint,
            @RequestPart(name = "image", required = false) MultipartFile image
    ) {
        CommonResponse.CommonResponseBuilder<ComplaintDtoResponse> responseBuilder = CommonResponse.builder();

        try {
            UpdateComplaintRequest request = objectMapper.readValue(jsonComplaint, new TypeReference<>() {
            });
            request.setComplaintImage(image);

            ComplaintDtoResponse complainDtoResponse = complaintService.updateComplaint(request);

            responseBuilder.statusCode(HttpStatus.OK.value());
            responseBuilder.message(ResponseMessage.SUCCESS_UPDATE_DATA);
            responseBuilder.data(complainDtoResponse);

            return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
        } catch (Exception e) {
            responseBuilder.message(ResponseMessage.ERROR_INTERNAL_SERVER);
            responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'TECHNICIAN')")
    @GetMapping
    public ResponseEntity<CommonResponse<List<GetComplaintDtoResponse>>> getAllComplaint(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "complaint_date") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        SearchComplaintRequest request = SearchComplaintRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<GetComplaintDtoResponse> complaints = complaintService.getAllComplaints(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(complaints.getTotalPages())
                .totalElement(complaints.getTotalElements())
                .page(complaints.getPageable().getPageNumber() + 1)
                .size(complaints.getPageable().getPageSize())
                .hasNext(complaints.hasNext())
                .hasPrevious(complaints.hasPrevious())
                .build();

        CommonResponse<List<GetComplaintDtoResponse>> response = CommonResponse.<List<GetComplaintDtoResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(complaints.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> deleteById(@PathVariable String id) {
        complaintService.deleteComplaint(id);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','TECHNICIAN')")
    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<String>> updateComplaintStatus(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "status") Integer status) {
        complaintService.updateComplaintStatusWithPatch(id, status);

        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .build();

        return ResponseEntity.ok(response);
    }

}
