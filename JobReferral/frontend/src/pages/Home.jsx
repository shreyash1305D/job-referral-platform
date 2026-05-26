import React, { useState, useEffect } from 'react';
import { useAuth } from '../auth/useAuth';
import { jobsAPI } from '../api/jobs';
import JobCard from '../components/JobCard';
import LoadingSpinner from '../components/LoadingSpinner';
import { Search, Filter, MapPin, Briefcase } from 'lucide-react';
import toast from 'react-hot-toast';
import '../styles/home.css';

const Home = () => {
  const [jobs, setJobs] = useState([]);
  const [filteredJobs, setFilteredJobs] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');
  const [filterType, setFilterType] = useState('ALL');
  const [filterLocation, setFilterLocation] = useState('');
  const { user } = useAuth();

  useEffect(() => {
    fetchJobs();
  }, []);

  useEffect(() => {
    filterJobs();
  }, [jobs, searchTerm, filterType, filterLocation]);

  const fetchJobs = async () => {
    try {
      const response = await jobsAPI.getAllJobs();
      setJobs(response.data);
    } catch (error) {
      toast.error('Failed to fetch jobs');
    } finally {
      setLoading(false);
    }
  };

  const filterJobs = () => {
    let filtered = jobs;

    if (searchTerm) {
      filtered = filtered.filter(
        (job) =>
          job.jobTitle.toLowerCase().includes(searchTerm.toLowerCase()) ||
          job.description.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    if (filterType !== 'ALL') {
      filtered = filtered.filter((job) => job.jobType === filterType);
    }

    if (filterLocation) {
      filtered = filtered.filter((job) =>
        job.location.toLowerCase().includes(filterLocation.toLowerCase())
      );
    }

    setFilteredJobs(filtered);
  };

  return (
    <div className="home-page">
      <div className="home-header">
        <div className="header-content">
          <h1>Find Your Dream Job</h1>
          <p>Discover amazing opportunities and get referrals from top professionals</p>
        </div>
      </div>

      <div className="home-container">
        <div className="search-section">
          <div className="search-bar">
            <Search size={20} />
            <input
              type="text"
              placeholder="Search jobs by title, keyword..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
            />
          </div>

          <div className="filter-section">
            <div className="filter-group">
              <label>Job Type</label>
              <select
                value={filterType}
                onChange={(e) => setFilterType(e.target.value)}
              >
                <option value="ALL">All Types</option>
                <option value="FULL_TIME">Full Time</option>
                <option value="PART_TIME">Part Time</option>
                <option value="CONTRACT">Contract</option>
                <option value="INTERN">Internship</option>
              </select>
            </div>

            <div className="filter-group">
              <label>Location</label>
              <div className="location-input">
                <MapPin size={18} />
                <input
                  type="text"
                  placeholder="City or region..."
                  value={filterLocation}
                  onChange={(e) => setFilterLocation(e.target.value)}
                />
              </div>
            </div>

            {(searchTerm || filterType !== 'ALL' || filterLocation) && (
              <button
                className="clear-filters"
                onClick={() => {
                  setSearchTerm('');
                  setFilterType('ALL');
                  setFilterLocation('');
                }}
              >
                Clear Filters
              </button>
            )}
          </div>
        </div>

        {loading ? (
          <LoadingSpinner />
        ) : filteredJobs.length === 0 ? (
          <div className="empty-state">
            <Briefcase size={48} />
            <h3>No jobs found</h3>
            <p>Try adjusting your search or filters</p>
          </div>
        ) : (
          <>
            <div className="results-info">
              <p>Showing {filteredJobs.length} job{filteredJobs.length !== 1 ? 's' : ''}</p>
            </div>

            <div className="jobs-grid">
              {filteredJobs.map((job) => (
                <JobCard
                  key={job.id}
                  job={job}
                  onRefresh={fetchJobs}
                />
              ))}
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default Home;