import groupHandler from './group';
import reviewHandler from './review';

const handlers = [...reviewHandler, ...groupHandler];

export default handlers;
