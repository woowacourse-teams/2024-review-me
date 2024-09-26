const hasFinalConsonant = (word: string) => {
  const code = word.charCodeAt(word.length - 1);
  return (code - 0xac00) % 28 !== 0;
};

export default hasFinalConsonant;
