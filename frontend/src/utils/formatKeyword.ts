const formatKeyword = (content: string) => {
  const contentWithoutExample = Array.from(content.split(' (예: ')[0]);
  const emoji = contentWithoutExample.shift();
  const keyword = contentWithoutExample.join('');

  return `${emoji} ${keyword}`;
};

export default formatKeyword;
