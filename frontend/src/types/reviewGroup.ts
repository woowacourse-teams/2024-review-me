export interface PasswordResponse {
  status: 'valid' | 'error' | 'invalid';
  error?: Error;
}
export interface ReviewGroupData {
  revieweeName: string;
  projectName: string;
}
