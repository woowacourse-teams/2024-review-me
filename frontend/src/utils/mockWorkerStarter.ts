const startMockWorker = async () => {
  if (process.env.NODE_ENV === 'production') return;

  const { worker } = await import('../mocks/browser');
  worker.start();
};

export default startMockWorker;
