import React, { useState } from 'react';
import { X } from 'lucide-react';
import { referralsAPI } from '../api/referrals';
import toast from 'react-hot-toast';
import '../styles/modal.css';

const ReferralModal = ({ jobId, jobTitle, onClose }) => {
  const [formData, setFormData] = useState({
    candidateName: '',
    candidateEmail: '',
    candidatePhone: '',
    candidateBio: '',
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      await referralsAPI.createReferral({
        ...formData,
        jobPosting: { id: jobId },
      });
      toast.success('Referral submitted successfully!');
      onClose();
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to submit referral');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="modal-close" onClick={onClose}>
          <X size={24} />
        </button>

        <div className="modal-header">
          <h2>Refer a Candidate</h2>
          <p>{jobTitle}</p>
        </div>

        <form onSubmit={handleSubmit} className="referral-form">
          <div className="form-group">
            <label>Candidate Name *</label>
            <input
              type="text"
              name="candidateName"
              value={formData.candidateName}
              onChange={handleChange}
              required
              placeholder="Full Name"
            />
          </div>

          <div className="form-group">
            <label>Candidate Email *</label>
            <input
              type="email"
              name="candidateEmail"
              value={formData.candidateEmail}
              onChange={handleChange}
              required
              placeholder="Email Address"
            />
          </div>

          <div className="form-group">
            <label>Candidate Phone *</label>
            <input
              type="tel"
              name="candidatePhone"
              value={formData.candidatePhone}
              onChange={handleChange}
              required
              placeholder="Phone Number"
            />
          </div>

          <div className="form-group">
            <label>Candidate Bio / Cover Letter</label>
            <textarea
              name="candidateBio"
              value={formData.candidateBio}
              onChange={handleChange}
              placeholder="Tell us about the candidate..."
              rows="4"
            />
          </div>

          <div className="modal-footer">
            <button type="button" className="btn btn-secondary" onClick={onClose}>
              Cancel
            </button>
            <button type="submit" className="btn btn-primary" disabled={loading}>
              {loading ? 'Submitting...' : 'Submit Referral'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ReferralModal;