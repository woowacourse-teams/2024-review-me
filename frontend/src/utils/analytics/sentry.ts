import * as Sentry from '@sentry/react';

const isProduction = process.env.NODE_ENV === 'production';
const baseUrlPattern = new RegExp(`^${process.env.API_BASE_URL?.replace(/[-\/\\^$*+?.()|[\]{}]/g, '\\$&')}`);

const initializeSentry = () => {
  Sentry.init({
    dsn: `${process.env.SENTRY_DSN}`,
    enabled: isProduction,
    integrations: [Sentry.browserTracingIntegration()],
    environment: 'production',
    tracesSampleRate: 1.0,
    tracePropagationTargets: [baseUrlPattern],
  });
};

export default initializeSentry;
