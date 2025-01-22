export const gettingPath = (pageName: string, isForMember: boolean = false) => {
  let basic = 'user';
  if (isForMember) basic += '/logged-in';
  return `${basic}/${pageName}`;
};
