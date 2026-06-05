"use strict";

const chineseZodiacs = ["Rat", "Ox", "Tiger", "Rabbit", "Dragon", "Snake", "Horse", "Goat", "Monkey", "Rooster", "Dog", "Pig"];
const chineseImages = ["./Chinese Zodaic/images/Rat.gif", "./Chinese Zodaic/images/Ox.gif", "./Chinese Zodaic/images/Tiger.gif", "./Chinese Zodaic/images/Rabbit.gif", "./Chinese Zodaic/images/Dragon.gif", "./Chinese Zodaic/images/Snake.gif", "./Chinese Zodaic/images/Horse.gif", "./Chinese Zodaic/images/Goat.gif", "./Chinese Zodaic/images/Monkey.gif", "./Chinese Zodaic/images/Rooster.gif","./Chinese Zodaic/images/Dog.gif", "./Chinese Zodaic/images/Pig.gif"];


document.write('<table>');
document.write('<tr>');

for (let i = 0; i < chineseZodiacs.length; i++) {
    let img = document.createElement("img");
    img.src = chineseImages[i];
    document.write('<th>');
    document.write(chineseZodiacs[i] + '<img src="./Chinese Zodaic/images/'+ chineseZodiacs[i] + '.gif"');
    document.write('</th>');
}
document.write('</tr>')
for (let i = 1912; i <= 2023; i++) {
    if ((i - 1912) % chineseZodiacs.length == 0) {
        document.write('<tr>');
    }
    document.write('<td>' + i + '</td>');
}
document.write('</tr>')
document.write('</table>')