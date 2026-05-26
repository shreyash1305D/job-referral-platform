import React from 'react';
import '../styles/footer.css';

const Footer = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="footer">
      <div className="footer-container">
        <div className="footer-section">
          <h4>JobReferral</h4>
          <p>Connecting talented professionals with their dream jobs through smart referrals.</p>
          <div className="social-links">
            <a href="#" title="LinkedIn" aria-label="LinkedIn">
              <span className="social-icon">in</span>
            </a>
            <a href="#" title="Twitter" aria-label="Twitter">
              <span className="social-icon">𝕏</span>
            </a>
            <a href="#" title="GitHub" aria-label="GitHub">
              <span className="social-icon">⚙</span>
            </a>
            <a href="#" title="Email" aria-label="Email">
              <span className="social-icon">✉</span>
            </a>
          </div>
        </div>

        <div className="footer-section">
          <h5>Quick Links</h5>
          <ul>
            <li><a href="/">Jobs</a></li>
            <li><a href="/about">About Us</a></li>
            <li><a href="/contact">Contact</a></li>
            <li><a href="/blog">Blog</a></li>
          </ul>
        </div>

        <div className="footer-section">
          <h5>Resources</h5>
          <ul>
            <li><a href="/faq">FAQ</a></li>
            <li><a href="/privacy">Privacy Policy</a></li>
            <li><a href="/terms">Terms of Service</a></li>
            <li><a href="/support">Support</a></li>
          </ul>
        </div>

        <div className="footer-section">
          <h5>Company</h5>
          <ul>
            <li><a href="/careers">Careers</a></li>
            <li><a href="/partners">Partners</a></li>
            <li><a href="/press">Press Kit</a></li>
            <li><a href="/contact">Contact Us</a></li>
          </ul>
        </div>
      </div>

      <div className="footer-bottom">
        <p>
          Made with ❤️ by JobReferral Team
        </p>
        <p>&copy; {currentYear} JobReferral. All rights reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;