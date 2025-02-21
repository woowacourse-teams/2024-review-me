import groupHandler from './group';
import highlightHandler from './highlight';
import oAuthHandler from './oAuth';
import reviewHandler from './review';

const handlers = [...reviewHandler, ...groupHandler, ...highlightHandler, ...oAuthHandler];

export default handlers;
