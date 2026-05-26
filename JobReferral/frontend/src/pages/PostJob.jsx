import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { jobsAPI } from '../api/jobs';
import toast from 'react-hot-toast';
import { Briefcase, MapPin, DollarSign, Clock } from 'lucide-react';
import '../styles/post-job.css';

const PostJob = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    jobTitle: '',
    description: '',
    location: '',
    salary: '',
    jobType: 'FULL_TIME',
    experience: '',
    requirements: '',
    benefits: '',
  });

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
      await jobsAPI.createJob(formData);
      toast.success('Job posted successfully!');
      navigate('/my-jobs');
    } catch (error) {
      toast.error(error.response?.data?.message || 'Failed to post job');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="post-job-page">
      <div className="post-job-container">
        <div className="post-job-header">
          <h1>Post a New Job</h1>
          <p>Share an opportunity with top professionals</p>
        </div>

        <form onSubmit={handleSubmit} className="post-job-form">
          <div className="form-section">
            <h2>Basic Information</h2>

            <div className="form-group">
              <label htmlFor="jobTitle">Job Title *</label>
              <div className="input-wrapper">
                <Briefcase size={20} />
                <input
                  type="text"
                  id="jobTitle"
                  name="jobTitle"
                  placeholder="e.g., Senior React Developer"
                  value={formData.jobTitle}
                  onChange={handleChange}
                  required
                />
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="location">Location *</label>
                <div className="input-wrapper">
                  <MapPin size={20} />
                  <input
                    type="text"
                    id="location"
                    name="location"
                    placeholder="e.g., New York, NY"
                    value={formData.location}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>

              <div className="form-group">
                <label htmlFor="salary">Salary Range *</label>
                <div className="input-wrapper">
                  <DollarSign size={20} />
                  <input
                    type="text"
                    id="salary"
                    name="salary"
                    placeholder="e.g., $80k - $120k"
                    value={formData.salary}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label htmlFor="jobType">Job Type *</label>
                <select
                  id="jobType"
                  name="jobType"
                  value={formData.jobType}
                  onChange={handleChange}
                >
                  <option value="FULL_TIME">Full Time</option>
                  <option value="PART_TIME">Part Time</option>
                  <option value="CONTRACT">Contract</option>
                  <option value="INTERN">Internship</option>
                </select>
              </div>

              <div className="form-group">
                <label htmlFor="experience">Experience Level *</label>
                <div className="input-wrapper">
                  <Clock size={20} />
                  <input
                    type="text"
                    id="experience"
                    name="experience"
                    placeholder="e.g., 3-5 years"
                    value={formData.experience}
                    onChange={handleChange}
                    required
                  />
                </div>
              </div>
            </div>
          </div>

          <div className="form-section">
            <h2>Job Details</h2>

            <div className="form-group">
              <label htmlFor="description">Job Description *</label>
              <textarea
                id="description"
                name="description"
                placeholder="Describe the job responsibilities and what you're looking for..."
                value={formData.description}
                onChange={handleChange}
                rows="5"
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="requirements">Requirements</label>
              <textarea
                id="requirements"
                name="requirements"
                placeholder="List the required skills and qualifications..."
                value={formData.requirements}
                onChange={handleChange}
                rows="4"
              />
            </div>

            <div className="form-group">
              <label htmlFor="benefits">Benefits</label>
              <textarea
                id="benefits"
                name="benefits"
                placeholder="Describe the benefits and perks..."
                value={formData.benefits}
                onChange={handleChange}
                rows="4"
              />
            </div>
          </div>

          <div className="form-actions">
            <button
              type="button"
              className="btn btn-secondary"
              onClick={() => navigate('/my-jobs')}
            >
              Cancel
            </button>
            <button
              type="submit"
              className="btn btn-primary"
              disabled={loading}
            >
              {loading ? 'Posting...' : 'Post Job'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default PostJob;