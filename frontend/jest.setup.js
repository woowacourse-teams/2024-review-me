import server from './src/mocks/server';
import { matchers } from '@emotion/jest';

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());
afterAll(() => server.close());

expect.extend(matchers);
