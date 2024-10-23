import groupHandler from './group';
import highlightHandler from './highlight';
import reviewHandler from './review';

const handlers = [...reviewHandler, ...groupHandler, ...highlightHandler];

export default handlers;
