import client from './client';

export const referralsAPI = {
  createReferral: (data) => client.post('/referrals', data),
  getReferralsByJob: (jobId) => client.get(`/referrals/job/${jobId}`),
  getMyReferrals: () => client.get('/referrals/my-referrals'),
  updateReferralStatus: (id, status) => client.put(`/referrals/${id}/status`, { status }),
  getReferralById: (id) => client.get(`/referrals/${id}`),
};