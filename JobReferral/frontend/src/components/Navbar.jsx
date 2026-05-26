import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/useAuth';
import { Menu, X, LogOut, User, Briefcase } from 'lucide-react';
import '../styles/navbar.css';

const Navbar = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
    setMenuOpen(false);
  };

  const handleNavClick = (path) => {
    navigate(path);
    setMenuOpen(false);
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-logo">
          <Briefcase size={28} className="logo-icon" />
          <span>JobReferral</span>
        </Link>

        <button
          className="menu-toggle"
          onClick={() => setMenuOpen(!menuOpen)}
        >
          {menuOpen ? <X size={24} /> : <Menu size={24} />}
        </button>

        <div className={`nav-menu ${menuOpen ? 'active' : ''}`}>
          {user ? (
            <>
              <Link to="/" className="nav-link" onClick={() => setMenuOpen(false)}>
                Jobs
              </Link>

              {user.role === 'RECRUITER' && (
                <>
                  <Link to="/post-job" className="nav-link" onClick={() => setMenuOpen(false)}>
                    Post Job
                  </Link>
                  <Link to="/my-jobs" className="nav-link" onClick={() => setMenuOpen(false)}>
                    My Jobs
                  </Link>
                </>
              )}

              {user.role === 'CANDIDATE' && (
                <Link to="/my-referrals" className="nav-link" onClick={() => setMenuOpen(false)}>
                  My Referrals
                </Link>
              )}

              {user.role === 'ADMIN' && (
                <Link to="/admin" className="nav-link" onClick={() => setMenuOpen(false)}>
                  Admin
                </Link>
              )}

              <div className="nav-user">
                <Link to="/profile" className="nav-link user-link" onClick={() => setMenuOpen(false)}>
                  <User size={18} />
                  {user.firstName}
                </Link>
                <button className="logout-btn" onClick={handleLogout}>
                  <LogOut size={18} />
                  Logout
                </button>
              </div>
            </>
          ) : (
            <>
              <Link to="/login" className="nav-link" onClick={() => setMenuOpen(false)}>
                Login
              </Link>
              <Link to="/register" className="nav-link nav-register" onClick={() => setMenuOpen(false)}>
                Register
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;