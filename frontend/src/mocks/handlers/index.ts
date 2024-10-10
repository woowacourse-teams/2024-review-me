import collectionHandler from './collection';
import groupHandler from './group';
import reviewHandler from './review';

const handlers = [...reviewHandler, ...groupHandler, ...collectionHandler];

export default handlers;
