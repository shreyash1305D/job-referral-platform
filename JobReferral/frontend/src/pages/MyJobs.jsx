import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { jobsAPI } from '../api/jobs';
import { referralsAPI } from '../api/referrals';
import LoadingSpinner from '../components/LoadingSpinner';
import toast from 'react-hot-toast';
import { Edit, Trash2, Eye, Users } from 'lucide-react';
import '../styles/myjobs.css';

const MyJobs = () => {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedJob, setSelectedJob] = useState(null);
  const [referrals, setReferrals] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const response = await jobsAPI.getRecruiterJobs();
      setJobs(response.data);
    } catch (error) {
      toast.error('Failed to fetch jobs');
    } finally {
      setLoading(false);
    }
  };

  const fetchReferrals = async (jobId) => {
    try {
      const response = await referralsAPI.getReferralsByJob(jobId);
      setReferrals(response.data);
      setSelectedJob(jobId);
    } catch (error) {
      toast.error('Failed to fetch referrals');
    }
  };

  const handleDelete = async (jobId) => {
    if (window.confirm('Are you sure you want to delete this job?')) {
      try {
        await jobsAPI.deleteJob(jobId);
        toast.success('Job deleted successfully');
        fetchJobs();
      } catch (error) {
        toast.error('Failed to delete job');
      }
    }
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="my-jobs-page">
      <div className="my-jobs-container">
        <div className="page-header">
          <h1>My Job Postings</h1>
          <button
            className="btn btn-primary"
            onClick={() => navigate('/post-job')}
          >
            Post New Job
          </button>
        </div>

        {jobs.length === 0 ? (
          <div className="empty-state">
            <Eye size={48} />
            <h3>No jobs posted yet</h3>
            <p>Start by posting your first job</p>
            <button
              className="btn btn-primary"
              onClick={() => navigate('/post-job')}
            >
              Post Your First Job
            </button>
          </div>
        ) : (
          <div className="jobs-list">
            {jobs.map((job) => (
              <div key={job.id} className="job-item">
                <div className="job-item-header">
                  <div>
                    <h3>{job.jobTitle}</h3>
                    <p>{job.location}</p>
                  </div>
                  <div className="job-item-actions">
                    <button
                      className="action-btn view"
                      title="View Referrals"
                      onClick={() => fetchReferrals(job.id)}
                    >
                      <Users size={18} />
                      Referrals
                    </button>
                    <button
                      className="action-btn edit"
                      title="Edit"
                      onClick={() => navigate(`/edit-job/${job.id}`)}
                    >
                      <Edit size={18} />
                    </button>
                    <button
                      className="action-btn delete"
                      title="Delete"
                      onClick={() => handleDelete(job.id)}
                    >
                      <Trash2 size={18} />
                    </button>
                  </div>
                </div>

                <p className="job-description">
                  {job.description?.substring(0, 150)}...
                </p>

                <div className="job-meta">
                  <span className="meta-badge">{job.jobType}</span>
                  <span className="meta-badge">{job.salary}</span>
                  <span className="meta-badge">{job.experience}</span>
                </div>
              </div>
            ))}
          </div>
        )}

        {selectedJob && (
          <div className="referrals-panel">
            <div className="panel-header">
              <h2>Referrals for Job #{selectedJob}</h2>
              <button
                className="close-btn"
                onClick={() => {
                  setSelectedJob(null);
                  setReferrals([]);
                }}
              >
                ✕
              </button>
            </div>

            {referrals.length === 0 ? (
              <p className="no-referrals">No referrals yet</p>
            ) : (
              <div className="referrals-list">
                {referrals.map((referral) => (
                  <div key={referral.id} className="referral-item">
                    <h4>{referral.candidateName}</h4>
                    <p>
                      <strong>Email:</strong> {referral.candidateEmail}
                    </p>
                    <p>
                      <strong>Phone:</strong> {referral.candidatePhone}
                    </p>
                    {referral.candidateBio && (
                      <p>
                        <strong>Bio:</strong> {referral.candidateBio}
                      </p>
                    )}
                    <span className={`status-badge ${referral.status.toLowerCase()}`}>
                      {referral.status}
                    </span>
                  </div>
                ))}
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default MyJobs;