import React, { useState, useEffect } from 'react';
import { referralsAPI } from '../api/referrals';
import LoadingSpinner from '../components/LoadingSpinner';
import toast from 'react-hot-toast';
import { Heart, CheckCircle, XCircle, Clock } from 'lucide-react';
import '../styles/myreferrals.css';

const MyReferrals = () => {
  const [referrals, setReferrals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterStatus, setFilterStatus] = useState('ALL');

  useEffect(() => {
    fetchReferrals();
  }, []);

  const fetchReferrals = async () => {
    try {
      const response = await referralsAPI.getMyReferrals();
      setReferrals(response.data);
    } catch (error) {
      toast.error('Failed to fetch referrals');
    } finally {
      setLoading(false);
    }
  };

  const filteredReferrals = filterStatus === 'ALL'
    ? referrals
    : referrals.filter((ref) => ref.status === filterStatus);

  const getStatusIcon = (status) => {
    switch (status) {
      case 'ACCEPTED':
        return <CheckCircle size={20} className="status-icon accepted" />;
      case 'REJECTED':
        return <XCircle size={20} className="status-icon rejected" />;
      default:
        return <Clock size={20} className="status-icon pending" />;
    }
  };

  if (loading) {
    return <LoadingSpinner />;
  }

  return (
    <div className="my-referrals-page">
      <div className="my-referrals-container">
        <div className="page-header">
          <h1>My Referrals</h1>
          <p className="subtitle">Track all your candidate referrals</p>
        </div>

        <div className="filter-tabs">
          <button
            className={`filter-tab ${filterStatus === 'ALL' ? 'active' : ''}`}
            onClick={() => setFilterStatus('ALL')}
          >
            All ({referrals.length})
          </button>
          <button
            className={`filter-tab ${filterStatus === 'PENDING' ? 'active' : ''}`}
            onClick={() => setFilterStatus('PENDING')}
          >
            Pending ({referrals.filter(r => r.status === 'PENDING').length})
          </button>
          <button
            className={`filter-tab ${filterStatus === 'ACCEPTED' ? 'active' : ''}`}
            onClick={() => setFilterStatus('ACCEPTED')}
          >
            Accepted ({referrals.filter(r => r.status === 'ACCEPTED').length})
          </button>
          <button
            className={`filter-tab ${filterStatus === 'REJECTED' ? 'active' : ''}`}
            onClick={() => setFilterStatus('REJECTED')}
          >
            Rejected ({referrals.filter(r => r.status === 'REJECTED').length})
          </button>
        </div>

        {filteredReferrals.length === 0 ? (
          <div className="empty-state">
            <Heart size={48} />
            <h3>No referrals yet</h3>
            <p>Start referring candidates to see them here</p>
          </div>
        ) : (
          <div className="referrals-grid">
            {filteredReferrals.map((referral) => (
              <div key={referral.id} className="referral-card">
                <div className="referral-header">
                  <div>
                    <h3>{referral.candidateName}</h3>
                    <p className="job-title">{referral.jobPosting?.jobTitle}</p>
                  </div>
                  <div className={`status-badge ${referral.status.toLowerCase()}`}>
                    {getStatusIcon(referral.status)}
                    <span>{referral.status}</span>
                  </div>
                </div>

                <div className="referral-content">
                  <p>
                    <strong>Email:</strong> <a href={`mailto:${referral.candidateEmail}`}>{referral.candidateEmail}</a>
                  </p>
                  <p>
                    <strong>Phone:</strong> <a href={`tel:${referral.candidatePhone}`}>{referral.candidatePhone}</a>
                  </p>

                  {referral.candidateBio && (
                    <div className="bio">
                      <strong>About:</strong>
                      <p>{referral.candidateBio}</p>
                    </div>
                  )}
                </div>

                <div className="referral-footer">
                  <small>{new Date(referral.createdAt).toLocaleDateString()}</small>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
};

export default MyReferrals;