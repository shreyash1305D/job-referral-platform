import client from './client';

export const jobsAPI = {
  getAllJobs: () => client.get('/jobs'),
  getJobById: (id) => client.get(`/jobs/${id}`),
  createJob: (data) => client.post('/jobs', data),
  updateJob: (id, data) => client.put(`/jobs/${id}`, data),
  deleteJob: (id) => client.delete(`/jobs/${id}`),
  getRecruiterJobs: () => client.get('/jobs/recruiter/list'),
};