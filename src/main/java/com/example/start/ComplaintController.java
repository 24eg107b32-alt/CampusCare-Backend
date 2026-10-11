
package com.example.start;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:5174",
    "http://localhost:5175"
})
public class ComplaintController {

    private final ComplaintRepository complaintRepository;

    public ComplaintController(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    // Submit a new complaint
    @PostMapping
    public Complaint submitComplaint(@RequestBody Complaint complaint) {
        complaint.setStatus("Pending");
        return complaintRepository.save(complaint);
    }

    // Get all complaints
    @GetMapping
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // Track a complaint by ID
    @GetMapping("/{id}")
    public Complaint getComplaintById(@PathVariable Long id) {
        return complaintRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Complaint not found with ID: " + id
            ));
    }

    // Update complaint status
    @PutMapping("/{id}/status")
    public Complaint updateStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        Complaint complaint = complaintRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Complaint not found with ID: " + id
            ));

        String status = request.get("status");

        if (!"Pending".equals(status)
                && !"In Progress".equals(status)
                && !"Resolved".equals(status)) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Invalid complaint status"
            );
        }

        complaint.setStatus(status);

        return complaintRepository.save(complaint);
    }
}
