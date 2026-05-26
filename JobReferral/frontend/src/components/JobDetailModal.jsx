import React, { useState } from 'react';
import { X, Heart } from 'lucide-react';
import ReferralModal from './ReferralModal';
import '../styles/modal.css';

const JobDetailModal = ({ job, onClose, onRefresh }) => {
  const [showReferral, setShowReferral] = useState(false);

  return (
    <>
      <div className="modal-overlay" onClick={onClose}>
        <div className="modal-content" onClick={(e) => e.stopPropagation()}>
          <button className="modal-close" onClick={onClose}>
            <X size={24} />
          </button>

          <div className="modal-header">
            <h2>{job.jobTitle}</h2>
            <p className="modal-company">{job.recruiter?.company}</p>
          </div>

          <div className="modal-body">
            <section className="modal-section">
              <h3>Job Description</h3>
              <p>{job.description}</p>
            </section>

            <section className="modal-section">
              <h3>Requirements</h3>
              <p>{job.requirements}</p>
            </section>

            <section className="modal-section">
              <h3>Benefits</h3>
              <p>{job.benefits}</p>
            </section>

            <section className="modal-section">
              <div className="info-grid">
                <div className="info-item">
                  <strong>Location:</strong>
                  <p>{job.location}</p>
                </div>
                <div className="info-item">
                  <strong>Salary:</strong>
                  <p>{job.salary}</p>
                </div>
                <div className="info-item">
                  <strong>Type:</strong>
                  <p>{job.jobType}</p>
                </div>
                <div className="info-item">
                  <strong>Experience:</strong>
                  <p>{job.experience}</p>
                </div>
              </div>
            </section>
          </div>

          <div className="modal-footer">
            <button className="btn btn-secondary" onClick={onClose}>
              Close
            </button>
            <button
              className="btn btn-primary"
              onClick={() => setShowReferral(true)}
            >
              <Heart size={16} />
              Make Referral
            </button>
          </div>
        </div>
      </div>

      {showReferral && (
        <ReferralModal
          jobId={job.id}
          jobTitle={job.jobTitle}
          onClose={() => {
            setShowReferral(false);
            onRefresh?.();
          }}
        />
      )}
    </>
  );
};

export default JobDetailModal;