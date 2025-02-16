export interface PasswordResponse {
  status: 'valid' | 'error' | 'invalid';
  error?: Error;
}
export interface ReviewGroupData {
  revieweeName: string;
  projectName: string;
}

export interface DataForReviewRequestCode {
  revieweeName: string;
  projectName: string;
  groupAccessCode?: string;
}
