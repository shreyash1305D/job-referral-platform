import React, { useState } from 'react';
import { MapPin, Briefcase, DollarSign, Clock, Eye } from 'lucide-react';
import JobDetailModal from './JobDetailModal';
import '../styles/jobcard.css';

const JobCard = ({ job, onRefresh }) => {
  const [showDetails, setShowDetails] = useState(false);

  return (
    <>
      <div className="job-card">
        <div className="job-card-header">
          <h3 className="job-title">{job.jobTitle}</h3>
          <span className="job-type-badge">{job.jobType}</span>
        </div>

        <p className="job-company">{job.recruiter?.company || 'Company'}</p>

        <p className="job-description">
          {job.description?.substring(0, 100)}...
        </p>

        <div className="job-details">
          <div className="detail-item">
            <MapPin size={16} />
            <span>{job.location}</span>
          </div>
          <div className="detail-item">
            <DollarSign size={16} />
            <span>{job.salary}</span>
          </div>
          <div className="detail-item">
            <Briefcase size={16} />
            <span>{job.experience}</span>
          </div>
          <div className="detail-item">
            <Clock size={16} />
            <span>2 days ago</span>
          </div>
        </div>

        <button
          className="view-details-btn"
          onClick={() => setShowDetails(true)}
        >
          <Eye size={16} />
          View Details
        </button>
      </div>

      {showDetails && (
        <JobDetailModal
          job={job}
          onClose={() => setShowDetails(false)}
          onRefresh={onRefresh}
        />
      )}
    </>
  );
};

export default JobCard;