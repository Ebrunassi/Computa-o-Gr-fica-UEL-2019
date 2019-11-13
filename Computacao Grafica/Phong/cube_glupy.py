# -----------------------------------------------------------------------------
# Copyright (c) 2009-2016 Nicolas P. Rougier. All rights reserved.
# Distributed under the (new) BSD License.
# -----------------------------------------------------------------------------
import numpy as np
from glumpy import app, gl, glm, gloo, data
from time import sleep


alpha =0

def cube():
    vtype = [('a_position', np.float32, 3),
             ('a_texcoord', np.float32, 2),
             ('a_normal',   np.float32, 3)]
    itype = np.uint32
    # vertices dos cubos
    p = np.array([
        # cubo 1
        [0,0,0],        [-1.5,0,0],     [-1.5,-1.5,0],      [0,-1.5,0],
        [0,-1.5,-1.5],  [0,0,-1.5],     [-1.5,0,-1.5],      [-1.5,-1.5,-1.5],
        # cubo 2
        [2,0,0],        [0.5,0,0],      [0.5,-1.5,0],   [2,-1.5,0],
        [2,-1.5,-1.5],  [2,0,-1.5],     [0.5,0,-1.5],   [0.5,-1.5,-1.5]], dtype=float)
    # Vetor normal para cada face, 
    # como as faces de ambos os cubos são iguais,
    # nao tem motivo pra duplicar isso.
    n = np.array([
        # cubo 
        [0,0,1],[1,0,0],[0,1,0],[-1,0,1],[0,-1,0],[0,0,-1],
        ])
    # Texture coords
    t = np.array([[0, 0], [0, 1], [1, 1], [1, 0]])

    faces_p = [
        # cubo 1
        0, 1, 2, 3,
        0, 3, 4, 5,
        0, 5, 6, 1,
        1, 6, 7, 2,
        7, 4, 3, 2,
        4, 7, 6, 5,
        #    cubo 2
        8,  9, 10, 11,
        8, 11, 12, 13,
        8, 13, 14,  9,
        9, 14, 15, 10,
        15, 12, 11, 10,
        12, 15, 14, 13]
    faces_n = [0, 0, 0, 0,  1, 1, 1, 1,   2, 2, 2, 2,
               3, 3, 3, 3,  4, 4, 4, 4,   5, 5, 5, 5,
               0, 0, 0, 0,  1, 1, 1, 1,   2, 2, 2, 2,
               3, 3, 3, 3,  4, 4, 4, 4,   5, 5, 5, 5]
    faces_t = [0, 1, 2, 3,  0, 1, 2, 3,   0, 1, 2, 3,
               3, 2, 1, 0,  0, 1, 2, 3,   0, 1, 2, 3, 0, 1, 2, 3,  0, 1, 2, 3,   0, 1, 2, 3,
               3, 2, 1, 0,  0, 1, 2, 3,   0, 1, 2, 3]

    vertices = np.zeros(48, vtype)
    # organiza os vertices na ordem das faces
    vertices['a_position'] = p[faces_p]
    # mesma coisa para a normal
    vertices['a_normal'] = n[faces_n]
    # e para a textura
    # vertices['a_texcoord'] = t[faces_t]
    
    # CUBO 1
    # gera o modelo dos triangulos . 
    index_cubo1 = np.resize(np.array([0, 1, 2, 0, 2, 3], dtype=itype), 6 * (2 * 3))
    # soma e gera o restante dos triangulos
    index_cubo1 += np.repeat(4 * np.arange(6, dtype=itype), 6)

    # CUBO 2
    # gera o modelo dos triangulos . 
    index_cubo2 = np.resize(np.array([24, 25, 26, 24, 26, 27], dtype=itype), 6 * (2 * 3))
    # soma e gera o restante dos triangulos
    index_cubo2 += np.repeat(4 * np.arange(6, dtype=itype), 6)

    #  tira o view pra ambos os dados
    vertices = vertices.view(gloo.VertexBuffer)
    index_cubo1 = index_cubo1.view(gloo.IndexBuffer)
    index_cubo2 = index_cubo2.view(gloo.IndexBuffer)

    return vertices, index_cubo1, index_cubo2


vertex = """
uniform mat4   u_model;         // Model matrix
uniform mat4   u_view;          // View matrix
uniform mat4   u_projection;    // Projection matrix
attribute vec3 a_position;      // Vertex position
attribute vec3 a_normal;        // Vertex normal
attribute vec2 a_texcoord;      // Vertex texture coordinates
varying vec3   v_normal;        // Interpolated normal (out)
varying vec3   v_position;      // Interpolated position (out)
varying vec2   v_texcoord;      // Interpolated fragment texture coordinates (out)

void main()
{
    // Assign varying variables
    v_normal   = a_normal;
    v_position = a_position;
    v_texcoord = a_texcoord;

    // Final position
    gl_Position = u_projection * u_view * u_model * vec4(a_position,1.0);
}
"""

fragment = """
uniform mat4      u_model;           // Model matrix
uniform mat4      u_view;            // View matrix
uniform mat4      u_normal;          // Normal matrix
uniform mat4      u_projection;      // Projection matrix
uniform sampler2D u_texture;         // Texture 
uniform vec3      u_light_position;  // Light position
uniform vec3      u_light_intensity; // Light intensity

varying vec3      v_normal;          // Interpolated normal (in)
varying vec3      v_position;        // Interpolated position (in)
varying vec2      v_texcoord;        // Interpolated fragment texture coordinates (in)
void main()
{
    // Calculate normal in world coordinates
    vec3 normal = normalize(u_normal * vec4(v_normal,1.0)).xyz;

    // Calculate the location of this fragment (pixel) in world coordinates
    vec3 position = vec3(u_view*u_model * vec4(v_position, 1));

    // Calculate the vector from this pixels surface to the light source
    vec3 surfaceToLight = u_light_position - position;

    // Calculate the cosine of the angle of incidence (brightness)
    float brightness = dot(normal, surfaceToLight) /
                      (length(surfaceToLight) * length(normal));
    brightness = max(min(brightness,1.0),0.0);

    // Calculate final color of the pixel, based on:
    // 1. The angle of incidence: brightness
    // 2. The color/intensities of the light: light.intensities
    // 3. The texture and texture coord: texture(tex, fragTexCoord)

    // Get texture color
    vec4 t_color = vec4(texture2D(u_texture, v_texcoord).rgb, 1.0);

    // Final color
    gl_FragColor = t_color * (0.1 + 0.9*brightness * vec4(u_light_intensity, 1));
}
"""

window = app.Window(width=1024, height=1024,
                    color=(0.30, 0.30, 0.35, 1.00))


@window.event
def on_draw(dt):
    window.clear()
    gl.glEnable(gl.GL_DEPTH_TEST)
    cube.draw(gl.GL_TRIANGLES, indices)
    cube.draw(gl.GL_TRIANGLES, an)
    # gl.glTextureView
    # cube.draw()
    

@window.event
def on_key_press(symbol, modifiers):
    global alpha
    dx=dy=dz=0
    shift = True if modifiers == 1 else False
    
    mat = np.eye(4, dtype=np.float32)
    letra = ''
    if symbol==-1:
        return
    letra = chr(symbol)
    if letra =='W':
        dz=-0.1
    elif letra == 'S':
        dz=0.1
    elif letra=='D':
        dx=0.1
    elif letra =='A':
        dx=-0.1
    elif letra ==' ':
        dy=0.1
    elif letra =='Z':
        dy=-0.1
    elif letra =='R':
        alpha+=1
        glm.rotate(mat,alpha,0,1,0)
        cube['u_model'] = mat
    elif letra =='Q':
        alpha-=1
        glm.rotate(mat,alpha,0,1,0)
        cube['u_model'] = mat
        
    glm.translate(mat, dx,dy,dz)
    view = cube['u_view'].reshape(4, 4)
    x,y,z = cube["u_light_position"] 
    print('[',x,'],[',y,'],[',z,']')
    cube["u_light_position"] = x+dx,y+dy,z+dz
    cube['u_normal'] = np.array(np.matrix(np.dot(view, mat)).I.T)
    sleep(0.01)
    


@window.event
def on_resize(width, height):
    cube['u_projection'] = glm.perspective(
        45.0, width / float(height), 2.0, 100.0)


@window.event
def on_init():
    gl.glEnable(gl.GL_DEPTH_TEST)
    gl.glDisable(gl.GL_BLEND)


vertices, indices, an = cube()
cube = gloo.Program(vertex, fragment)
cube.bind(vertices)

cube["u_light_position"] = 0.2, -0.5, -3
cube["u_light_intensity"] = 4
cube['u_texture'] = data.get("crate.png")
# cv2.imwrite('crate.png',data.get("crate.png"))
cube['u_model'] = np.eye(4, dtype=np.float32)
cube['u_view'] = glm.translation(0, 0, -5)
phi, theta = 40, 30

app.run()
