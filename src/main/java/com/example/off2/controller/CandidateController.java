package com.example.off2.controller;

import com.example.off2.model.Candidate;
import com.example.off2.model.FamilyMember;
import com.example.off2.repository.CandidateRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidates")
@CrossOrigin(origins = "*")
public class CandidateController {

    private final CandidateRepository candidateRepository;

    public CandidateController(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    // ================= Save Candidate + Family =================
    @PostMapping
    @Transactional
    public Candidate addCandidate(@RequestBody Candidate candidate) {

        // ✅ Link family members to candidate
        if (candidate.getFamilyMembers() != null) {
            for (FamilyMember fm : candidate.getFamilyMembers()) {
                fm.setCandidate(candidate);
            }
        }

        return candidateRepository.save(candidate);
    }

    // ================= GET : Fetch All Candidates =================
    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    // ================= GET : Fetch Single Candidate =================
    @GetMapping("/{id}")
    public Candidate getCandidateById(@PathVariable Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }
}
