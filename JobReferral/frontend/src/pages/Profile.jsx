import React, { useState, useEffect } from 'react';
import { useAuth } from '../auth/useAuth';
import { authAPI } from '../api/auth';
import toast from 'react-hot-toast';
import { User, Mail, Building, FileText, Save } from 'lucide-react';
import '../styles/profile.css';

const Profile = () => {
  const { user, updateUser } = useAuth();
  const [isEditing, setIsEditing] = useState(false);
  const [loading, setLoading] = useState(false);
  const [formData, setFormData] = useState({
    firstName: user?.firstName || '',
    lastName: user?.lastName || '',
    bio: user?.bio || '',
    profilePhotoUrl: user?.profilePhotoUrl || '',
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
      const response = await authAPI.updateProfile(formData);
      updateUser(response.data);
      toast.success('Profile updated successfully!');
      setIsEditing(false);
    } catch (error) {
      toast.error('Failed to update profile');
    } finally {
      setLoading(false);
    }
  };

  if (!user) {
    return <div>Loading...</div>;
  }

  return (
    <div className="profile-page">
      <div className="profile-container">
        <div className="profile-card">
          <div className="profile-header">
            <div className="profile-avatar">
              {user.profilePhotoUrl ? (
                <img src={user.profilePhotoUrl} alt={user.firstName} />
              ) : (
                <div className="avatar-placeholder">
                  {user.firstName[0]}{user.lastName[0]}
                </div>
              )}
            </div>
            <div className="profile-info">
              <h1>{user.firstName} {user.lastName}</h1>
              <p className="role-badge">{user.role}</p>
              <p className="company">{user.company}</p>
            </div>
          </div>

          {!isEditing ? (
            <>
              <div className="profile-details">
                <div className="detail-item">
                  <Mail size={20} />
                  <div>
                    <strong>Email</strong>
                    <p>{user.email}</p>
                  </div>
                </div>

                <div className="detail-item">
                  <Building size={20} />
                  <div>
                    <strong>Company</strong>
                    <p>{user.company}</p>
                  </div>
                </div>

                {user.bio && (
                  <div className="detail-item full-width">
                    <FileText size={20} />
                    <div>
                      <strong>Bio</strong>
                      <p>{user.bio}</p>
                    </div>
                  </div>
                )}
              </div>

              <button
                className="btn btn-primary"
                onClick={() => setIsEditing(true)}
              >
                <User size={16} />
                Edit Profile
              </button>
            </>
          ) : (
            <form onSubmit={handleSubmit} className="edit-form">
              <div className="form-group">
                <label>First Name</label>
                <input
                  type="text"
                  name="firstName"
                  value={formData.firstName}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Last Name</label>
                <input
                  type="text"
                  name="lastName"
                  value={formData.lastName}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Bio</label>
                <textarea
                  name="bio"
                  value={formData.bio}
                  onChange={handleChange}
                  placeholder="Tell us about yourself..."
                  rows="4"
                />
              </div>

              <div className="form-group">
                <label>Profile Photo URL</label>
                <input
                  type="url"
                  name="profilePhotoUrl"
                  value={formData.profilePhotoUrl}
                  onChange={handleChange}
                  placeholder="https://example.com/photo.jpg"
                />
              </div>

              <div className="form-actions">
                <button
                  type="button"
                  className="btn btn-secondary"
                  onClick={() => {
                    setIsEditing(false);
                    setFormData({
                      firstName: user.firstName,
                      lastName: user.lastName,
                      bio: user.bio || '',
                      profilePhotoUrl: user.profilePhotoUrl || '',
                    });
                  }}
                >
                  Cancel
                </button>
                <button type="submit" className="btn btn-primary" disabled={loading}>
                  <Save size={16} />
                  {loading ? 'Saving...' : 'Save Changes'}
                </button>
              </div>
            </form>
          )}
        </div>
      </div>
    </div>
  );
};

export default Profile;