const formattedCategories = <T extends { optionId: number; content: string }>(categories: T[]) => {
  return categories.map((category) => {
    const contentWithoutExample = Array.from(category.content.split(' (예: ')[0]);
    const emoji = contentWithoutExample.shift();
    const keyword = contentWithoutExample.join('');

    return {
      optionId: category.optionId,
      content: `${emoji} ${keyword}`,
    };
  });
};

export default formattedCategories;
